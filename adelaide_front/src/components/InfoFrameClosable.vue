<template>
  <div :class="resolveType">
    <a :class="resolveTypeClose">X</a>
    <slot></slot>
  </div>
</template>

<script>
export default {
  name: "InfoFrameClosable",
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
    },
    resolveTypeClose() {
      switch (this.type) {
        case "info":
          return "info-frame-close-general"
        case "success":
          return "info-frame-close-success"
        case "error":
          return "info-frame-close-error"
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

.info-frame-close-general, .info-frame-close-success, .info-frame-close-error {
  margin-top: -11px;
  margin-right: -11px;
  float: right;
  border-radius: 0 var(--border-radius-general) 0 var(--border-radius-general);
  padding: var(--padding-button-general);
  cursor: pointer;
}

.info-frame-close-general {
  background: var(--background-frame-close-button-general);
}

.info-frame-close-success {
  background: var(--background-frame-close-button-success);
}

.info-frame-close-error {
  background: var(--background-frame-close-button-error);
}

.info-frame-close-general:hover {
  background: var(--background-frame-close-button-general-hover);
}

.info-frame-close-success:hover {
  background: var(--background-frame-close-button-success-hover);
}

.info-frame-close-error:hover {
  background: var(--background-frame-close-button-error-hover);
}
</style>