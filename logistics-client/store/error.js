import { ref, computed } from 'vue'
import { defineStore } from 'pinia'
import { useToast } from '@/components/ui/toast/use-toast'
import { useRouter } from 'vue-router'

export const useErrorStore = defineStore('error', () => {
  const { toast } = useToast()
  const router = useRouter()
  const _message = ref('')
  const _fieldErrorMessages = ref([])
  const _statusCode = ref(0)
  const _title = ref('')

  const setFieldError = (field, error) => {
    _fieldErrorMessages.value.push({ path: field, message: error })
  }

  const message = computed(() => {
    return _message.value.trim()
  })

  const statusCode = computed(() => {
    return _statusCode.value
  })

  const title = computed(() => {
    return _title.value.trim()
  })

  const fieldMessage = (fieldName) => {
    if (!_fieldErrorMessages.value) return ''
    const fieldError = _fieldErrorMessages.value.find((field) => field.path.includes(fieldName))
    return fieldError ? fieldError.message.charAt(0).toUpperCase() + fieldError.message.slice(1) : ''
  }

  const resetMessages = () => {
    _message.value = ''
    _fieldErrorMessages.value = []
    _statusCode.value = 0
    _title.value = ''
  }

  const hasFieldErrors = () => {
    return _fieldErrorMessages.value.length > 0
  }

  const setErrorMessages = (mainMessage, fieldMessages, status = 0, titleMessage = '') => {
    _message.value = mainMessage
    _fieldErrorMessages.value = fieldMessages
    _statusCode.value = status
    _title.value = titleMessage

    let toastMessage = mainMessage
    switch (status) {
      case 401:
        toastMessage = mainMessage ?? 'You are not authorized to access the server API!'
        break
      case 403:
        toastMessage = mainMessage ?? `You are forbidden to access the server resource! `
        router.push('/error/403')
        break
      case 404:
        toastMessage = mainMessage ?? 'Server resource not found!'
        break
      default:
        toastMessage = `An error occurred! ${mainMessage}`
    }
    toast({
      title: titleMessage,
      description: toastMessage,
      variant: 'red'
    })
  }

  const showError = (message, title = '') => {
    _message.value = message
    _title.value = title
    toast({
      title: title,
      description: message,
      variant: 'red'
    })
  }
  return {
    message,
    statusCode,
    title,
    showError,
    fieldMessage,
    hasFieldErrors,
    setFieldError,
    resetMessages,
    setErrorMessages
  }
})
