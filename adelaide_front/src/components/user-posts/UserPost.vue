<template>
  <div class="user-post-block general-block">
    <header class="block-header">
      <router-link class="post-author" :to="'/'+post.author.username">
        <div class="post-avatar">
          <img :src="post.author.avatar" alt="avatar"/>
        </div>
        <div class="post-author-name">
          {{ post.author.firstName + " " + post.author.lastName }}
        </div>
        <div class="post-time">
          28 December 1997, 13:00
        </div>
      </router-link>
      <div class="post-title">{{ post.title }}</div>
      <div class="post-tags">
        <router-link to="/" class="post-tag">first-post</router-link>
        <router-link to="/" class="post-tag">some-test-tag</router-link>
        <router-link to="/" class="post-tag">some-very-long-testing-purpose-tag</router-link>
      </div>
    </header>
    <div class="post-content">
      {{ post.content }}
    </div>
    <div class="post-footer">
      <Button>Like</Button>
      <Button>Dislike</Button>
      <Button>Comments</Button>
      <div class="post-button-right">
        <Button>Edit</Button>
        <Button>Delete</Button>
      </div>
    </div>
  </div>
</template>

<script setup>
import Button from "../common/form/Button.vue";

const props = defineProps({
  post: {}
})
</script>

<style scoped>
.user-post-block {
  display: flex;
  flex-direction: column;
  padding: 0.75rem;
  gap: 0.5rem;
  font-family: var(--font-family);
  font-size: 1rem;
}

.block-header {
  display: flex;
  flex-direction: column;
  //gap: 0.5rem;
}

.post-author {
  display: flex;
  flex-direction: row;
  gap: 1rem;
  padding: 0 0.25rem 0 0;
  cursor: pointer;
  border-radius: var(--border-radius);
  align-items: flex-start;
}

.post-author-name {
  font-size: 1.4rem;
}

.post-author > * {
  cursor: pointer;
}

.post-time {
  margin-left: auto;
}

.post-avatar {
  max-height: 48px;
}

.post-avatar img {
  border-radius: var(--border-radius);
  cursor: pointer;
}

.post-author:hover {
  background: var(--background-hover);
}

.post-tags {
  display: flex;
  flex-direction: row;
  gap: 0.25rem;
}

.post-tag {
  background: var(--button-background);
  color: var(--button-color);
  padding: 0.1rem 0.25rem 0.1rem 0.25rem;
  cursor: pointer;
  border-radius: var(--border-radius);
  justify-content: center;
}

.post-tag:hover {
  background: var(--background-hover);
}

.post-title {
  font-size: 1.4rem;
}

.post-footer {
  display: flex;
  flex-direction: row;
  gap: 0.25rem;
}

.post-button-right {
  display: flex;
  flex-direction: row;
  gap: 0.25rem;
  margin-left: auto;
}
</style>