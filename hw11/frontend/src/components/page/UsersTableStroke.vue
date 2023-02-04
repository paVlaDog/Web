<template>
  <tr>
    <td class="user-id">{{ user.id }}</td>
    <td class="user-login">{{ user.login }}</td>
    <td class="user-name">{{ user.name }}</td>
  </tr>
</template>

<script>
export default {
  name: "UsersTableStroke",
  props: ["user"]
}
</script>

<style scoped>

</style>
