<template>
    <div class="tictactoe-row">
      <TicTacToeCell v-for="(val, i) in rowVal" :row="row" :col="i" :val="val" :key="i" :crossesMove="crossesMove"/>
    </div>
</template>


<script>
import TicTacToeCell from "@/components/TicTacToe/TicTacToeCell";
export default {
  name: "TicTacToeRow",
  components: {TicTacToeCell},
  props: ["row", "rowVal", "crossesMove"],
}
</script>

<style scoped>

</style>