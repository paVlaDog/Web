<template>
  <article>
    <div class="title">
      <a href="#" @click.prevent="onLinkToPostPage(post)">
        {{post.title}}
      </a>
    </div>
    <div class="information">
      By
      <a class="post-userLogin">{{userLogin}}</a>,
      <a class="post-userId">{{post.id}}</a>
      days ago, translation
    </div>
    <div class="body">{{post.text}}</div>
    <div class="footer">
      <div class="left">
        <img src="../../assets/img/voteup.png" title="Vote Up" alt="Vote Up"/>
        <span class="positive-score post-userId">{{post.id}}</span>
        <img src="../../assets/img/votedown.png" title="Vote Down" alt="Vote Down"/>
      </div>
      <div class="right">
        <img src="../../assets/img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
        <a class="post-userId"></a>{{post.id}} <a>days ago</a>
        <img src="../../assets/img/comments_16x16.png" title="Comments" alt="Comments"/>
        <a class="post-commentsCount" href="#">{{ commentsCount }}</a>
      </div>
    </div>
  </article>
</template>

<script>
export default {
    name: "Article",
    props: ["post", "userLogin", "commentsCount"],
    methods: {
      onLinkToPostPage: function (post) {
        this.$root.$emit("onLinkToPostPage", post);
      }
    }
}
</script>

<style scoped>

</style>
