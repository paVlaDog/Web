<template>
    <form @submit.prevent="move" class="tictactoe-form">
      <input type="hidden" name="action" value="onMove">
      <input :disabled="!!this.curVal" type="submit" class="tictactoe-cell" :value="visibleCell()" >
    </form>
</template>

<script>

  export default {
    name: "TicTacToeCell",
    props: ["row", "col", "val", "crossesMove"],
    data: function () {
      return {
        curVal: this.val,
      }
    },
    methods: {
      move: function () {
        this.$root.$emit("onMove", this.row, this.col)
        this.curVal = this.crossesMove;
        this.$root.$emit("changeCrossesMove");
      },
      visibleCell: function () {
        return this.curVal ? this.curVal : " ";
      }
    },
  }
</script>

<style scoped>

</style>
