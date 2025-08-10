<script setup lang="ts">
interface TextareaProps {
  modelValue?: string
  placeholder?: string
  disabled?: boolean
  readonly?: boolean
  rows?: number
  class?: string
  maxLength?: number
}

const props = defineProps<TextareaProps>()

const emit = defineEmits<{
  (e: 'update:modelValue', v: string): void
}>()

const onInput = (e: Event) => {
  const target = e.target as HTMLTextAreaElement
  emit('update:modelValue', target.value)
}
</script>

<template>
  <textarea
    :value="props.modelValue"
    :placeholder="props.placeholder"
    :disabled="props.disabled"
    :readonly="props.readonly"
    :rows="props.rows ?? 4"
    :maxlength="props.maxLength"
    @input="onInput"
    :class="[
      'w-full rounded-lg border border-input bg-background px-3 py-2 text-sm outline-none',
      'focus-visible:ring-2 focus-visible:ring-ring disabled:opacity-50 disabled:pointer-events-none',
      props.class
    ]"
  />
</template>
