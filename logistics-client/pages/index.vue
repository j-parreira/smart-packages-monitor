<template>
  <div class="w-full p-3 bg-slate-50 border-b">
    <Breadcrumb>
      <BreadcrumbList>
        <BreadcrumbItem>
          <BreadcrumbLink @click="$router.push('/')"> Home </BreadcrumbLink>
        </BreadcrumbItem>
      </BreadcrumbList>
    </Breadcrumb>
  </div>
  <div class="my-auto sm:w-full">
    <div class="mt-10 sm:mx-auto sm:w-full">
      <div class="flex justify-center items-center flex-col gap-5">
        <img src="/assets/img/logo.png" alt="logo" class="w-40" />
        <h2 class="text-2xl font-extrabold text-center text-gray-900 pb-10">Welcome to Smart Packages Management</h2>
        <hr class="w-1/2 pb-5" />
        <!-- list of persons who have developed the project -->
        <span class="text-center text-slate-600">
          Developed by: Diogo Abegão, João Parreira, Marcelo Oliveira and Pedro Barbeiro
        </span>
        <span>
          DAE - 2024/2025 - ESTG - IPLeiria
        </span>
      </div>
    </div>
  </div>
</template>
