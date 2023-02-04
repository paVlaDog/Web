<template>
    <form @submit.prevent="changeCell" class="tictactoe-form">
      <input type="hidden" name="action" value="onMove">
      <input :disabled="dis" type="submit" class="tictactoe-cell" :value="valCell" >
    </form>
</template>

<script>

  export default {
    name: "TicTacToeCell",
    props: ["row", "col", "val"],
    data: function () {
      return {
        dis: this.valCell === "0" || this.valCell === "X",
        valCell: this.val,
        curTurn: "X"
      }
    },
    methods: {
      changeCell: function () {
        this.$root.$emit("onChangeCell", this.row, this.col);
        this.valCell = this.curTurn;
        this.dis = 1;
        // this.$forceUpdate()
      },
    },
    beforeCreate() {
      this.$root.$on("onChangeCurTurn", (val) => {
        this.curTurn = val;
      },)
    },
  }
</script>

<style scoped>

</style>
