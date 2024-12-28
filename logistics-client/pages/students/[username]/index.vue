<template>
  <div v-if="student">
    <h2>Details of {{ username }}</h2>
    {{ student }}
  </div>

  <h2>Enrolled in:</h2>
  <div v-for="subject in student.subjects">
    {{ subject }}
  </div>

  <h2>Error messages:</h2>
  {{ messages }}
</template>

<script setup>
const route = useRoute();
const username = route.params.username;

const config = useRuntimeConfig();
const api = config.public.API_URL;

const { data: student, error: studentErr } = await useFetch(
  `${api}/student/${username}`
);

/*
const { data: subjects, error: subjectsErr } = await useFetch(
  `${api}/student/${username}/subjects`
);
*/

const messages = ref([]);

if (studentErr.value) messages.value.push(studentErr.value);
//if (subjectsErr.value) messages.value.push(subjectsErr.value);
</script>
