<script setup lang="ts">
interface AvatarProps {
  src?: string
  alt?: string
  size?: number | string // e.g., 32 or "2rem"
  rounded?: boolean
}

const props = withDefaults(defineProps<AvatarProps>(), {
  src: '',
  alt: '',
  size: 40,
  rounded: true
})

const emit = defineEmits<{
  (e: 'click'): void
}>()

const handleClick = () => {
  emit('click')
}
</script>

<template>
  <img
    :src="props.src || 'https://via.placeholder.com/150?text=Avatar'"
    :alt="props.alt"
    :width="props.size"
    :height="props.size"
    class="object-cover"
    :class="props.rounded ? 'rounded-full' : 'rounded-md'"
    @click="handleClick"
  />
</template>