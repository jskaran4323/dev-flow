<script setup lang="ts">
type Primitive = string | number

interface CheckboxProps {
  modelValue?: boolean | Primitive[]      // v-model: boolean or array
  value?: Primitive                        // used when modelValue is an array
  disabled?: boolean
  class?: string
  label?: string
  id?: string
}

const props = defineProps<CheckboxProps>()

const emit = defineEmits<{
  (e: 'update:modelValue', v: boolean | Primitive[]): void
}>()

const onChange = (e: Event) => {
  const target = e.target as HTMLInputElement
  const current = props.modelValue

  // Array mode
  if (Array.isArray(current)) {
    const next = [...current]
    const idx = next.findIndex(v => v === props.value)
    if (target.checked && idx === -1) next.push(props.value as Primitive)
    if (!target.checked && idx !== -1) next.splice(idx, 1)
    emit('update:modelValue', next)
    return
  }

  // Boolean mode
  emit('update:modelValue', target.checked)
}

const isChecked = () => {
  const current = props.modelValue
  if (Array.isArray(current)) {
    return current.includes(props.value as Primitive)
  }
  return Boolean(current)
}
</script>

<template>
  <label :for="id" class="inline-flex items-center gap-2 text-sm select-none cursor-pointer" :class="props.class">
    <input
      :id="id"
      type="checkbox"
      :checked="isChecked()"
      :disabled="disabled"
      @change="onChange"
      class="h-4 w-4 rounded border-input text-primary outline-none focus-visible:ring-2 focus-visible:ring-ring disabled:opacity-50 disabled:pointer-events-none"
    />
    <span v-if="label">{{ label }}</span>
    <slot v-else />
  </label>
</template>