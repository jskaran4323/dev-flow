<script setup lang="ts">
interface OptionItem {
  label: string
  value: string | number
  disabled?: boolean
}

interface SelectProps {
  modelValue?: string | number
  disabled?: boolean
  class?: string
  options?: OptionItem[] // optional; if omitted, use <slot> for custom <option>s
}

const props = defineProps<SelectProps>()

const emit = defineEmits<{
  (e: 'update:modelValue', v: string | number): void
}>()

const onChange = (e: Event) => {
  const target = e.target as HTMLSelectElement
  emit('update:modelValue', target.value)
}
</script>

<template>
  <select
    :value="props.modelValue"
    :disabled="props.disabled"
    @change="onChange"
    :class="[
      'w-full rounded-lg border border-input bg-background px-3 py-2 text-sm outline-none',
      'focus-visible:ring-2 focus-visible:ring-ring disabled:opacity-50 disabled:pointer-events-none',
      props.class
    ]"
  >
    <!-- If options prop is provided, render them -->
    <template v-if="props.options && props.options.length">
      <option
        v-for="opt in props.options"
        :key="String(opt.value)"
        :value="opt.value"
        :disabled="opt.disabled"
      >
        {{ opt.label }}
      </option>
    </template>

    <!-- Otherwise let the consumer pass custom <option> via slot -->
    <slot v-else />
  </select>
</template>
