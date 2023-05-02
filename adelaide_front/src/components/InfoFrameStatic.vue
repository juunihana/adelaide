<template>
  <div :class="resolveType">
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "InfoFrameStatic",
  props: {
    type: "info"
  },
  computed: {
    resolveType() {
      switch (this.type) {
        case "info":
          return "info-frame-general"
        case "success":
          return "info-frame-success"
        case "error":
          return "info-frame-error"
      }
    }
  }
}
</script>

<style scoped>
.info-frame-general, .info-frame-success, .info-frame-error {
  margin-bottom: 10px;
  padding: 10px;
  border-radius: var(--border-radius-general);
  font-family: var(--text-font-general);
  font-size: var(--text-size-info-panel);
}

.info-frame-general {
  background: var(--background-info-frame-general);
  border: var(--border-info-frame-general);
  color: var(--text-color-info-frame-general);
}

.info-frame-success {
  background: var(--background-info-frame-success);
  border: var(--border-info-frame-success);
  color: var(--text-color-info-frame-success);
}

.info-frame-error {
  background: var(--background-info-frame-error);
  border: var(--border-info-frame-error);
  color: var(--text-color-info-frame-error);
}
</style>