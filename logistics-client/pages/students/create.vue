<template>
  <h2>Create Student</h2>
  <form @submit.prevent="create">
    <div>
      Username:
      <input v-model.trim="studentForm.username" type="text" />
      <span v-if="usernameError" class="error">
        ERROR: {{ usernameError }}</span
      >
    </div>
    <div>
      Password:
      <input v-model.trim="studentForm.password" type="password" />
      <span v-if="passwordError" class="error">
        ERROR: {{ passwordError }}</span
      >
    </div>
    <div>
      Name:
      <input v-model.trim="studentForm.name" type="text" />
      <span v-if="nameError" class="error"> ERROR: {{ nameError }}</span>
    </div>
    <div>
      E-mail:
      <input v-model.trim="studentForm.email" type="text" />
      <span v-if="emailError" class="error"> ERROR: {{ emailError }}</span>
    </div>
    <div>
      Course:
      <select v-model="studentForm.courseCode">
        <option value="">--- Please select Course ---</option>
        <option v-for="course in courses" :value="course.code">
          {{ course.name }}
        </option>
      </select>
      <span v-if="courseCodeError" class="error">
        ERROR: {{ courseCodeError }}</span
      >
    </div>
    <button type="reset">RESET</button>
    <button type="submit" :disabled="isFormInvalid">CREATE</button>
  </form>
  <hr />
  <pre>{{ messages }}</pre>
</template>
<script setup>
const studentForm = reactive({
  username: null,
  password: null,
  email: null,
  name: null,
  courseCode: null,
});

const messages = ref([]);
const config = useRuntimeConfig();
const api = config.public.API_URL;
const { data: courses } = await useFetch(`${api}/course`);

// Field validation rules...

const usernameError = computed(() => {
  // Se o campo estiver a null não existe validação -> a função computed retorna null.
  // Caso contrario vamos validar os campos e retornar uma mensagem de erro que será guardada na variavel usernameError.
  // No template (frontend) caso a variavel usernameError tenha algum valor a mesma é mostrada ao utilizador.
  // EX: <span v-if="usernameError" class="error">ERROR: {{ usernameError }}</span>
  // A mesma lógica é aplicada às restantes validações.
  if (studentForm.username === null) return null;
  if (!studentForm.username) return "username is required";
  if (studentForm.username.length < 3)
    return "username must be at least 3 characters";
  if (studentForm.username.length > 15)
    return "username must be at most 15 characters";
  return null;
});

const passwordError = computed(() => {
  if (studentForm.password === null) return null;
  if (!studentForm.password) return "password is required";
  if (studentForm.password.length < 5)
    return "password must be at least 5 characters";
  return null;
});

const nameError = computed(() => {
  if (studentForm.name === null) return null;
  if (!studentForm.name) return "name is required";
  if (studentForm.name.length < 5) return "name must be at least 5 characters";
  return null;
});

const emailError = computed(() => {
  if (studentForm.email === null) return null;
  if (!studentForm.email) return "email is required";
  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
  if (!emailRegex.test(studentForm.email))
    return "email must be a valid email address";
  return null;
});

const courseCodeError = computed(() => {
  if (studentForm.courseCode === null) return null;
  if (!studentForm.courseCode) return "course is required";
  if (isNaN(studentForm.courseCode)) return "course code must be a number";
  return null;
});

const isFormInvalid = computed(() => {
  return (
    usernameError.value ||
    passwordError.value ||
    nameError.value ||
    emailError.value ||
    courseCodeError.value
  );
});

async function create() {
  try {
    await $fetch(`${api}/students`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: studentForm,
      onResponse({ request, response, options }) {
        messages.value.push({
          method: options.method,
          request: request,
          status: response.status,
          statusText: response.statusText,
          payload: response._data,
        });
      },
    });
  } catch (e) {
    console.log(e);
  }
}
</script>
<style scoped>
.error {
  color: red;
}
</style>
