<template>
  <div>
    <Article v-for="post in posts" :post="post" :userLogin="userLogin(post.userId)"
             :commentsCount="commentsCount(post.id)" :key="post.id"/>
  </div>

</template>

<script>
import Article from "../utilComp/Article";

export default {
    name: "Index",
    props: ["posts", "users", "comments"],
    components: {
      Article
    },
    methods: {
      userLogin: function (userId) {
        return Object.values(this.users).find(u => u.id === userId).login;
      },
      commentsCount: function (postId) {
        return Object.values(this.comments).filter(c => c.postId === postId).length;
      }
    },
}
</script>

<style scoped>

</style>
