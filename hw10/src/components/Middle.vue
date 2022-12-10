<template>
    <div class="middle">
        <Sidebar :posts="viewPosts"/>
        <main>
            <Index :posts="posts" :users="users" :comments="comments" v-if="page === 'Index'"/>
            <Post :userId="userId" :post="post" :users="users" :comments="comments" v-if="page === 'Post'"/>
            <Enter v-if="page === 'Enter'"/>
            <WritePost v-if="page === 'WritePost'"/>
            <EditPost v-if="page === 'EditPost'"/>
            <Register v-if="page === 'Register'"/>
            <Users :users="users" v-if="page === 'Users'"/>
        </main>
    </div>
</template>

<script>
import Sidebar from "./sidebar/Sidebar";
import Index from "./page/Index";
import Enter from "./page/Enter";
import WritePost from "./page/WritePost";
import EditPost from "./page/EditPost";
import Register from "./page/Register";
import Users from "./page/Users";
import Post from "./page/Post";

export default {
    name: "Middle",
    data: function () {
        return {
            page: "Index",
            post: "noData"
        }
    },
    components: {
        WritePost,
        Enter,
        Index,
        Sidebar,
        EditPost,
        Register,
        Users,
        Post
    },
    props: ["userId", "posts", "users", "comments"],
    computed: {
        viewPosts: function () {
            return Object.values(this.posts).sort((a, b) => b.id - a.id).slice(0, 2);
        }
    },
    beforeCreate() {
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
