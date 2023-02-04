<template>
    <div class="tictactoe-row">
      <TicTacToeCell v-for="(num1, ind) in rowVal" :row="row" :col="ind" :val="num1" :key="ind"/>
    </div>
</template>


<script>
import TicTacToeCell from "@/components/page/TicTacToe/TicTacToeCell";
export default {
  name: "TicTacToeRow",
  components: {TicTacToeCell},
  props: ["row", "rowVal"],
  // data: function () {
  //   return {
  //     createArr: {
  //       1:"1",
  //       2:"2",
  //       3:"3",
  //       4:"4",
  //       5:"5",
  //       6:"6",
  //       7:"7"}
  //   }
  // },
}
</script>

<style scoped>

</style>