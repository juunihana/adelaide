<template>
  <div>
    <BlockHeader link="/">Photos</BlockHeader>
    <main>
      <div class="user-profile-photo">
        <img src="@/assets/photo_96.png" alt="photo"/>
      </div>
      <div class="user-profile-photo">
        <img src="@/assets/photo_96.png" alt="photo"/>
      </div>
      <div class="user-profile-photo">
        <img src="@/assets/photo_96.png" alt="photo"/>
      </div>
      <div class="user-profile-photo">
        <img src="@/assets/photo_96.png" alt="photo"/>
      </div>
      <div class="user-profile-photo">
        <img src="@/assets/photo_96.png" alt="photo"/>
      </div>
      <div class="user-profile-photo">
        <img src="@/assets/photo_96.png" alt="photo"/>
      </div>
    </main>
  </div>
</template>

<script>
import BlockHeader from "@/components/common/BlockHeader.vue";

export default {
  name: "UserProfilePhotos",
  components: {BlockHeader}
}
</script>

<style scoped>
main {
  display: grid;
  grid-row-gap: 1em;
  grid-template-columns: repeat(3, 1fr);
  grid-template-rows: repeat(2, 1fr);
}

.user-profile-photo {
  text-align: center;
}
</style>