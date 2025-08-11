<script setup lang="ts">
interface SkeletonProps {
  /** preset shapes: text line, rectangle block, or circle (avatar) */
  variant?: 'text' | 'rect' | 'circle'
  /** width/height utilities, e.g., w-40 h-4 (ignored for text unless provided) */
  widthClass?: string
  heightClass?: string
  /** rounds: e.g., rounded, rounded-lg, rounded-full (auto for circle) */
  roundedClass?: string
  /** extra classes (margin, etc.) */
  class?: string
}

const props = withDefaults(defineProps<SkeletonProps>(), {
  variant: 'text',
  widthClass: '',
  heightClass: '',
  roundedClass: ''
})

const computeClasses = () => {
  const base = 'animate-pulse bg-muted'
  if (props.variant === 'circle') {
    return [base, 'rounded-full', props.widthClass || 'w-10', props.heightClass || 'h-10', props.class].join(' ')
  }
  if (props.variant === 'rect') {
    return [base, props.roundedClass || 'rounded-lg', props.widthClass || 'w-full', props.heightClass || 'h-24', props.class].join(' ')
  }
  // text line
  return [base, props.roundedClass || 'rounded', props.widthClass || 'w-full', props.heightClass || 'h-4', props.class].join(' ')
}
</script>

<template>
  <div :class="computeClasses()" />
</template>
