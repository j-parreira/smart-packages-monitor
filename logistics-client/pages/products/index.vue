<template>
  <div class="w-full p-3 bg-slate-50 border-b flex justify-between items-center">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
        <BreadcrumbSeparator />
        <BreadcrumbItem>
          <BreadcrumbPage>Products</BreadcrumbPage>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.back"> Back </BreadcrumbLink>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  </div>
  <div class="mb-auto sm:w-full p-3">
    <div v-if="isLoading" class="flex justify-center items-center">
      <Icon name="eos-icons:three-dots-loading" class="text-blue-950 w-12 h-12" />
    </div>
    <div v-else>
      <h1 class="text-slate-700 pb-3">List of Products</h1>
      <div class="flex justify-end pb-3">
        <AlertDialog>
          <AlertDialogTrigger>
            <Button variant="blue">Add Product</Button>
          </AlertDialogTrigger>
          <AlertDialogContent>
            <AlertDialogHeader>
              <AlertDialogTitle>Add Product</AlertDialogTitle>
              <AlertDialogDescription>
                <div class="flex flex-col gap-3">
                  <div class="flex flex-row items-center gap-3">
                    <span class="w-1/2 text-right"> Product Name </span>
                    <Input v-model="product.name" label="Name" />
                  </div>

                  <div class="flex flex-row items-center gap-3">
                    <span class="w-1/2 text-right"> Category </span>
                    <Select v-model="product.type" label="Category">
                      <SelectTrigger>
                        <SelectValue />
                      </SelectTrigger>
                      <SelectContent>
                        <SelectItem value="ELECTRONICS">Electronics</SelectItem>
                        <SelectItem value="FROZEN_FOOD">Frozen Food</SelectItem>
                        <SelectItem value="FRUITS">Fruits</SelectItem>
                        <SelectItem value="CARBONATED_DRINKS">Drinks</SelectItem>
                      </SelectContent>
                    </Select>
                  </div>
                </div>
              </AlertDialogDescription>
            </AlertDialogHeader>
            <AlertDialogFooter>
              <AlertDialogCancel>Cancel</AlertDialogCancel>
              <AlertDialogAction @click="createProduct()">Save</AlertDialogAction>
            </AlertDialogFooter>
          </AlertDialogContent>
        </AlertDialog>
      </div>
      <div class="border rounded-lg">
        <Table>
          <TableHeader>
            <TableRow>
              <TableHead class="w-[100px]"> ID </TableHead>
              <TableHead>Name</TableHead>
              <TableHead>Type</TableHead>
              <TableHead>Orders</TableHead>
              <TableHead>Volumes</TableHead>
              <TableHead>Stock</TableHead>
              <TableHead class="w-6"></TableHead>
              <TableHead class="w-6"></TableHead>
            </TableRow>
          </TableHeader>
          <TableBody>
            <TableRow v-for="p in products" :key="p.id">
              <TableCell class="font-medium"> {{ p.id }} </TableCell>
              <TableCell>{{ p.name }}</TableCell>
              <TableCell>
                {{ p.type === 'ELECTRONICS' ? 'Eletronics' : p.type === 'FROZEN_FOOD' ? 'Frozen Food' : p.type === 'FRUITS' ? 'Fruits' : 'Drinks' }}
              </TableCell>
              <TableCell>
                <div class="text-nowrap flex flex-row items-center text-xs text-slate-500 hover:text-blue-600 cursor-pointer" @click="$router.push(`/products/${p.id}/orders`)">
                  Show Orders
                  <Icon name="stash:new-window-page-light" class="w-6 h-6" mode="svg" />
                </div>
              </TableCell>
              <TableCell>
                <div class="text-nowrap flex flex-row items-center text-xs text-slate-500 hover:text-blue-600 cursor-pointer" @click="$router.push(`/products/${p.id}/volumes`)">
                  Show Volumes
                  <Icon name="stash:new-window-page-light" class="w-6 h-6" mode="svg" />
                </div>
              </TableCell>
              <TableCell>
                <div v-if="p.stock">
                  <div>Total Stock: {{ p.stock }}</div>
                  <AlertDialog>
                    <AlertDialogTrigger>
                      <div class="text-nowrap flex flex-row items-center text-xs text-slate-500 hover:text-blue-600 cursor-pointer" @click="updateStock(p.id)">
                        Show Stock Per Warehouse
                        <Icon name="stash:new-window-page-light" class="w-6 h-6" mode="svg" />
                      </div>
                    </AlertDialogTrigger>
                    <AlertDialogContent>
                      <AlertDialogHeader>
                        <AlertDialogTitle>Stock Per Warehouse</AlertDialogTitle>
                        <AlertDialogDescription>
                          <div class="rounded-md border border-slate-200 my-3" v-for="s in currentProduct.stocks" :key="s.id">
                            <Table>
                              <TableBody>
                                <TableRow>
                                  <TableHead class="bg-slate-50 border-r text-right w-1/2">Warehouse</TableHead>
                                  <TableCell class="border-r">{{ s.warehouseId }}</TableCell>
                                </TableRow>
                                <TableRow>
                                  <TableHead class="bg-slate-50 border-r text-right w-1/2">Quantity</TableHead>
                                  <TableCell class="border-r">{{ s.quantity }}</TableCell>
                                </TableRow>
                              </TableBody>
                            </Table>
                          </div>
                          <div class="rounded-md border border-slate-200 my-3">
                            <Table>
                              <TableBody>
                                <TableRow>
                                  <TableHead class="bg-slate-50 border-r text-right w-1/2">Total Stock</TableHead>
                                  <TableCell class="border-r">{{ p.stock }}</TableCell>
                                </TableRow>
                              </TableBody>
                            </Table>
                          </div>
                        </AlertDialogDescription>
                      </AlertDialogHeader>
                      <AlertDialogFooter>
                        <AlertDialogCancel>Cancel</AlertDialogCancel>
                      </AlertDialogFooter>
                    </AlertDialogContent>
                  </AlertDialog>
                </div>
                <div v-else>
                  <div class="text-red-500">No stock available</div>
                </div>
                <AlertDialog>
                  <AlertDialogTrigger>
                    <div class="text-nowrap flex flex-row items-center text-xs gap-1 text-slate-500 hover:text-blue-600 cursor-pointer" @click="productStock.productId = p.id">
                      Add Stock
                      <PackagePlus class="h-5 w-5 stroke-1" />
                    </div>
                  </AlertDialogTrigger>
                  <AlertDialogContent>
                    <AlertDialogHeader>
                      <AlertDialogTitle>Add Stock of Product: {{ p.name }}</AlertDialogTitle>
                      <AlertDialogDescription>
                        <div class="flex flex-col gap-3">
                          <div class="flex flex-row items-center gap-3">
                            <span class="w-1/2 text-right"> Warehouse </span>
                            <Select v-model="productStock.warehouseId" label="Warehouse">
                              <SelectTrigger>
                                <SelectValue />
                              </SelectTrigger>
                              <SelectContent>
                                <SelectItem v-for="w in warehouses" :key="w.id" :value="String(w.id)">{{ w.name }}</SelectItem>
                              </SelectContent>
                            </Select>
                          </div>
                          <div class="flex flex-row items-center gap-3">
                            <span class="w-1/2 text-right"> Quantity </span>
                            <Input v-model="productStock.quantity" label="Name" type="number" min="0" />
                          </div>
                        </div>
                      </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                      <AlertDialogCancel>Cancel</AlertDialogCancel>
                      <AlertDialogAction @click="addStock(p.id)">Save</AlertDialogAction>
                    </AlertDialogFooter>
                  </AlertDialogContent>
                </AlertDialog>
              </TableCell>
              <TableCell>
                <Trash2 class="w-6 h-6 hover:text-red-600 cursor-pointer stroke-1" @click="deleteProduct(p.id)" />
              </TableCell>
              <TableCell>
                <AlertDialog>
                  <AlertDialogTrigger>
                    <Edit class="w-6 h-6 hover:text-blue-600 cursor-pointer stroke-1" @click="updateProduct(p.id)" />
                  </AlertDialogTrigger>
                  <AlertDialogContent>
                    <AlertDialogHeader>
                      <AlertDialogTitle>Edit - {{ currentProduct.name }}</AlertDialogTitle>
                      <AlertDialogDescription>
                        <div class="flex flex-col gap-3">
                          <div class="flex flex-row items-center gap-3">
                            <span class="w-1/2 text-right"> Product Name </span>
                            <Input v-model="product.name" label="Name" />
                          </div>
                          <div class="flex flex-row items-center gap-3">
                            <span class="w-1/2 text-right"> Category </span>
                            <Select v-model="product.type" label="Category">
                              <SelectTrigger>
                                <SelectValue />
                              </SelectTrigger>
                              <SelectContent>
                                <SelectItem value="ELECTRONICS">Electronics</SelectItem>
                                <SelectItem value="FROZEN_FOOD">Frozen Food</SelectItem>
                                <SelectItem value="FRUITS">Fruits</SelectItem>
                                <SelectItem value="CARBONATED_DRINKS">Drinks</SelectItem>
                              </SelectContent>
                            </Select>
                          </div>
                        </div>
                      </AlertDialogDescription>
                    </AlertDialogHeader>
                    <AlertDialogFooter>
                      <AlertDialogCancel>Cancel</AlertDialogCancel>
                      <AlertDialogAction @click="editProduct(p.id)">Save</AlertDialogAction>
                    </AlertDialogFooter>
                  </AlertDialogContent>
                </AlertDialog>
              </TableCell>
            </TableRow>
          </TableBody>
        </Table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { Edit, PackagePlus, Trash2 } from 'lucide-vue-next'
import { onMounted } from 'vue'
import { useAPI } from '~/store/storeAPI'
import { useErrorStore } from '~/store/error.js'

const products = ref([])
const isLoading = ref(true)
const api = useAPI()
const currentProduct = ref([])
const storeError = useErrorStore()
const warehouses = ref([])

const product = ref({
  name: '',
  type: ''
})

const productStock = ref({
  productId: '',
  warehouseId: '',
  quantity: ''
})

const editProduct = async (id) => {
  await api.updateProduct(id, product.value)
  await fetchData()
}

const deleteProduct = async (id) => {
  if (!confirm('Are you sure you want to delete this product?')) return
  await api.deleteProduct(id)
  await fetchData()
}

const createProduct = async () => {
  if (!product.value.name) storeError.showError('Product name is required')
  if (!product.value.type) storeError.showError('Product type is required')
  if (!product.value.name || !product.value.type) return
  await api.createProduct(product.value)
  await fetchData()
  product.value = {
    name: '',
    type: ''
  }
}

const updateStock = async (id) => {
  currentProduct.value = await api.getProductStock(id)
}

const updateProduct = async (id) => {
  currentProduct.value = await api.getProduct(id)
  product.value = {
    name: currentProduct.value.name,
    type: currentProduct.value.type
  }
}

const addStock = async (id) => {
  productStock.productId = id
  await api.createStock(id, productStock.value)
  await fetchData()
}
const fetchData = async () => {
  warehouses.value = await api.getWarehouses()
  products.value = await api.getProducts()
  products.value.forEach(async (p) => {
    p.stock = (await api.getProductTotalStock(p.id)) ?? 'N/A'
  })
}
onMounted(async () => {
  await fetchData()
  isLoading.value = false
})
</script>
