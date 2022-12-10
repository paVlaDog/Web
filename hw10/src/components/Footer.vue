<template>
    <footer>
      <p>We already have {{countPost}} posts and {{countUser}} users!</p>
      <a href="/">Codeforces</a> &copy; 2022 by Mike Mirzayanov
    </footer>
</template>

<script>
export default {
    name: "Footer",
    props: ["countPost", "countUser"]
}
</script>

<style scoped>

</style>