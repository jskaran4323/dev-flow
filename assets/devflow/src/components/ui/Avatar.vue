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
</script>

<template>
  <img
    :src="src || 'https://via.placeholder.com/150?text=Avatar'"
    :alt="alt"
    :width="size"
    :height="size"
    class="object-cover"
    :class="rounded ? 'rounded-full' : 'rounded-md'"
    @click="$emit('click')"
  />
</template>
