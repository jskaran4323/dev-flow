<script setup lang="ts">
interface InputProps {
  modelValue?: string | number
  type?: string
  placeholder?: string
  disabled?: boolean
  readonly?: boolean
  class?: string
}

const props = defineProps<InputProps>()

const emit = defineEmits<{
  (e: 'update:modelValue', v: string | number): void
}>()

const onInput = (e: Event) => {
  const target = e.target as HTMLInputElement
  emit('update:modelValue', target.value)
}
</script>

<template>
  <input
    :type="props.type || 'text'"
    :value="props.modelValue"
    :placeholder="props.placeholder"
    :disabled="props.disabled"
    :readonly="props.readonly"
    @input="onInput"
    :class="[
      'w-full rounded-lg border border-input bg-background px-3 py-2 text-sm outline-none',
      'focus-visible:ring-2 focus-visible:ring-ring disabled:opacity-50 disabled:pointer-events-none',
      props.class
    ]"
  />
</template>
