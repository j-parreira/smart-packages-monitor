<template>
    <div class="w-full p-3 bg-slate-50 border-b flex justify-between items-center">
        <Breadcrumb>
            <BreadcrumbList>
                <BreadcrumbItem>
                    <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
                </BreadcrumbItem>
                <BreadcrumbSeparator />
                <BreadcrumbItem>
                    <BreadcrumbPage>Store</BreadcrumbPage>
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
            <div class="border rounded-lg">
                <Table>
                    <TableHeader>
                        <TableRow>
                            <TableHead class="w-[100px]"> ID </TableHead>
                            <TableHead>Name</TableHead>
                            <TableHead>Type</TableHead>
                            <TableHead>Stock</TableHead>
                            <TableHead class="w-6"></TableHead>
                        </TableRow>
                    </TableHeader>
                    <TableBody>
                        <TableRow v-for="p in products" :key="p.id">
                            <TableCell class="font-medium"> {{ p.id }} </TableCell>
                            <TableCell>{{ p.name }}</TableCell>
                            <TableCell>
                                {{ p.type === 'ELECTRONICS' ? 'Eletronics' : p.type === 'FROZEN_FOOD' ? 'Frozen Food' :
                                    p.type === 'FRUITS' ? 'Fruits' : 'Drinks' }}
                            </TableCell>
                            <TableCell>
                                <div> {{ p.stock }}</div>
                            </TableCell>
                            <TableCell v-if="p.stock>0" class="flex justify-end flex-row text-nowrap gap-2 items-center text-slate-500 cursor-pointer" :class="{'hover:text-blue-600':!storeCart.isOnCart(p),'hover:text-red-600':storeCart.isOnCart(p)}" @click="toggleCartProduct(p)">
                                <span v-if="storeCart.isOnCart(p)">
                                    Remove
                                </span>
                                <span v-else>
                                    Add
                                </span>
                                <ShoppingCart class="h-4 w-4" />
                            </TableCell>
                            <TableCell v-else class="flex justify-end flex-row text-nowrap gap-2 items-center text-slate-500 cursor-not-allowed">
                                <span>
                                    Out of Stock
                                </span>
                            </TableCell>
                        </TableRow>
                    </TableBody>
                </Table>
            </div>
        </div>
    </div>
</template>

<script setup>
import { Plus, ShoppingCart } from 'lucide-vue-next'
import { onMounted } from 'vue'
import { useAPI } from '~/store/storeAPI'
import { useCart } from '~/store/storeCart'
const storeCart = useCart()

const products = ref([])
const isLoading = ref(true)
const api = useAPI()
const warehouses = ref([])

const toggleCartProduct = (product) => {
    if (storeCart.isOnCart(product)) {
        storeCart.removeFromCart(product)
    } else {
        storeCart.addToCart(product)
    }
}

const fetchData = async () => {
    warehouses.value = await api.getWarehouses()
    products.value = await api.getProducts()
    products.value.forEach(async (p) => {
        const stock = await api.getProductTotalStock(p.id)
        p.stock = stock == 0 ? '-' : stock
    })
}
onMounted(async () => {
    await fetchData()
    isLoading.value = false
})
</script>