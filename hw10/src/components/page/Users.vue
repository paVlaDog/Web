<template>
  <div class="users datatable">
    <div class="caption">User</div>
    <table>
      <thead>
      <tr>
        <th>Id</th>
        <th>Login</th>
        <th>Name</th>
      </tr>
      </thead>
      <tbody>
        <UsersTableStroke v-for="user in users" :user="user" :key="user.id"/>
      </tbody>
    </table>
  </div>
</template>

<script>
import UsersTableStroke from "../utilComp/UsersTableStroke";


export default {
    name: "Users",
    components: {
      UsersTableStroke
    },
    props: ["users"]
}
</script>

<style scoped>

</style>
