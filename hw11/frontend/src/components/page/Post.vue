<template>
  <div>
    <Article :post="post" :userLogin="post.user.login" :commentsCount="commentsCount(post.id)"/>
    <Comment v-for="comment in commentsById(post.id)" :comment="comment" :userLogin="comment.user.login" :key="comment.id"/>

    <div v-if="user.login" class="form" style="margin-top: 10px">
      <div class="header">Your comment:</div>
      <div class="body">
        <form @submit.prevent="onWriteComment">
          <div class="field">
            <div class="name">
              <label for="text">Text</label>
            </div>
            <div class="value">
              <textarea id="text" name="text" v-model="text"></textarea>
            </div>
          </div>
          <div class="error">{{ error }}</div>
          <div class="button-field">
            <input type="submit" value="Write">
          </div>
        </form>
      </div>
    </div>
  </div>

</template>

<script>
import Article from "../utilComp/Article";
import Comment from "@/components/utilComp/Comment";

export default {
    name: "Post",
    props: ["user","post", "comments"],
    components: {
      Comment,
      Article
    },
    data: function () {
      return {
        text: "",
        error: ""
      }
    },
    methods: {
      commentsById: function (postId) {
        return Object.values(this.comments).filter(c => c.post.id === postId);
      },
      commentsCount: function (postId) {
        return this.commentsById(postId).length;
      },
      onWriteComment: function () {
        this.error = "";
        this.$root.$emit("onWriteComment", this.text, this.post).then(() => {
          this.text = "";
        });
      }
    },
    beforeCreate() {
      this.$root.$on("onWriteCommentValidationError", (error) => this.error = error);
    }
}
</script>

<style scoped>

</style>
