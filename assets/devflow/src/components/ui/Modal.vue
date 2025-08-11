<script setup lang="ts">
import { watch, onMounted, onBeforeUnmount } from 'vue'

interface ModalProps {
  modelValue: boolean            // v-model: open/close
  title?: string                 // optional heading text
  widthClass?: string            // e.g., 'max-w-md', 'max-w-lg'
  closeOnEsc?: boolean
  closeOnBackdrop?: boolean
}

const props = withDefaults(defineProps<ModalProps>(), {
  modelValue: false,
  title: '',
  widthClass: 'max-w-lg',
  closeOnEsc: true,
  closeOnBackdrop: true
})

const emit = defineEmits<{
  (e: 'update:modelValue', v: boolean): void
  (e: 'close'): void
}>()

const close = () => {
  emit('update:modelValue', false)
  emit('close')
}

const onEsc = (e: KeyboardEvent) => {
  if (!props.modelValue) return
  if (props.closeOnEsc && e.key === 'Escape') close()
}

// Lock scroll when open
const lockScroll = (lock: boolean) => {
  const html = document.documentElement
  if (lock) {
    html.style.overflow = 'hidden'
  } else {
    html.style.overflow = ''
  }
}

watch(() => props.modelValue, (open) => lockScroll(open), { immediate: true })

onMounted(() => window.addEventListener('keydown', onEsc))
onBeforeUnmount(() => {
  window.removeEventListener('keydown', onEsc)
  lockScroll(false)
})
</script>

<template>
  <teleport to="body">
    <transition name="fade">
      <div
        v-if="modelValue"
        class="fixed inset-0 z-50 flex items-center justify-center p-4"
        aria-modal="true"
        role="dialog"
      >
        <!-- Backdrop -->
        <div
          class="absolute inset-0 bg-black/40"
          @click="closeOnBackdrop ? close() : null"
        />

        <!-- Panel -->
        <transition name="pop">
          <div
            class="relative z-10 w-full rounded-xl border border-border bg-card text-card-foreground shadow-soft"
            :class="widthClass"
          >
            <!-- Header -->
            <div class="flex items-center justify-between px-5 pt-4">
              <h3 v-if="title" class="text-lg font-semibold">{{ title }}</h3>
              <button
                type="button"
                aria-label="Close"
                class="inline-flex h-8 w-8 items-center justify-center rounded-md hover:bg-muted transition-colors"
                @click="close"
              >
                ✕
              </button>
            </div>

            <!-- Body -->
            <div class="px-5 py-4">
              <slot />
            </div>

            <!-- Footer -->
            <div v-if="$slots.footer" class="px-5 pb-5 pt-3 border-t border-border">
              <slot name="footer" />
            </div>
          </div>
        </transition>
      </div>
    </transition>
  </teleport>
</template>

<style scoped>
.fade-enter-active, .fade-leave-active { transition: opacity 0.15s ease; }
.fade-enter-from, .fade-leave-to { opacity: 0; }

.pop-enter-active, .pop-leave-active { transition: transform 0.18s ease, opacity 0.18s ease; }
.pop-enter-from, .pop-leave-to { transform: translateY(6px) scale(0.98); opacity: 0; }
</style>
