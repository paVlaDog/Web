<template>
  <div>
    <Article v-for="post in posts" :post="post" :commentsCount="commentsCount(post.id)" :userLogin="post.user.login" :key="post.id"/>
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
    commentsCount: function (postId) {
      return Object.values(this.comments).filter(c => c.post.id === postId).length;
    }
  },
  computed: {
    viewPosts: function () {
      return Object.values(this.posts).sort((a, b) => b.id - a.id);
    }
  },
}
</script>

<style scoped>

</style>
