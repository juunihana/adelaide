<template>
  <div>
    <div class="user-profile-container">
      <div class="user-avatar">

      </div>
      <div class="user-info-block">
        <div class="user-name">
          {{user.firstName}}
          {{user.middleName? user.middleName : ""}}
          {{user.lastName}}
          {{user.maidenSurname? "(" + user.maidenSurname + ")" : ""}}
        </div>
        <div class="user-status" v-if="user.status">
          {{user.status}}
        </div>
        <div class="user-age">
          {{user.age}} years old
        </div>
        <div class="user-info" v-if="user.info">
          {{user.info}}
        </div>
        <div class="user-place" v-if="user.place">
          From {{user.place}}
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import UserService from "../service/UserService"

const props = defineProps({
  username: String
})

let user = {}

UserService.getUserProfile(props.username)
.then((response) => {
  user = response.data
})
</script>

<style scoped>
.user-profile-container {
  display: flex;
  flex-direction: row;
}

.user-avatar {
  flex: 1;
}

.user-avatar img {
  border-radius: 5px;
}

.user-info-block {
  flex: 4;

  display: flex;
  flex-direction: column;
}

.user-name {
}

.user-profile-container
{
  font-family: Verdana, Arial, sans-serif;
  font-size: 1rem;
}
</style>