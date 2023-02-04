<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index :comments="comments" :users="users" :posts="posts" v-if="page === 'Index'" />
            <Enter v-if="page === 'Enter'"/>
            <Register v-if="page === 'Register'"/>
            <Users :users="users" v-if="page === 'Users'" />
            <WritePost v-if="page === 'WritePost'"></WritePost>
            <Post :user="user" :post="post" :comments="comments" v-if="page === 'Post'"/>
            <TicTacToeBoard :state="state" v-if="page === 'TicTacToe'"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./main/Index";
import Enter from "./main/Enter";
import Register from "./main/Register";
import Users from "@/components/page/Users";
import WritePost from "@/components/page/WritePost";
import Post from "@/components/page/Post";
import TicTacToeBoard from "@/components/TicTacToe/TicTacToeBoard";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index",
            post: "noData"
        }
    },
    components: {
      TicTacToeBoard,
      Post,
      WritePost,
      Users,
      Register,
      Enter,
      Index,
      Sidebar,
    },
    props: ["posts", "users", "comments", "user", "state"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        }
    }, beforeCreate() {
      this.$root.$on("onChangePage", (page) => this.page = page)
      this.$root.$on("onLinkToPostPage", (post) => {
        this.post = post
        this.page = "Post"
      })
    }
}
</script>

<style scoped>

</style>
