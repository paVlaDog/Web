<template>
    <div>
      {{userLogin}}: {{comment.text}}
    </div>
</template>

<script>
export default {
    name: "Comment",
    props: ["comment", "userLogin"]
}
</script>

<style scoped>

</style>
