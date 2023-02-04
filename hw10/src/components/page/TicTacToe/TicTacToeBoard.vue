<template>
  <div>
    <h1>Tic-Tac-Toe</h1>
        <div class="tictactoe-board">
          <TicTacToeRow v-for="(num, ind) in createArr" :row="ind" :key="ind" :rowVal="num"/>
        </div>
  </div>
</template>


<script>
  import TicTacToeRow from "@/components/page/TicTacToe/TicTacToeRow";
  import Vue from "vue";
  // import Vue from "vue";
  export default {
    name: "TicTacToeBoard",
    components: {TicTacToeRow},
    data: function () {
      return {
        createArr: [["X", "", "0"], ["", "", ""], ["", "", ""]],
        curTurn: "X",
        size: 3
      }
    },
    beforeCreate() {
      this.$root.$on("onChangeCell", (row, col) => this.selectCell(row, col))
    },
    methods: {
      initArr (size) {
        this.size=size;
        for (let i = 0; i <= size; i++) {
          this.createArr[i] = []
          for (let j = 0; j <= size; j++) {
            this.createArr[i][j] = ""
          }
        }
      },
      selectCell (row, col) {
        console.log(`row ${row} col ${col}`)
        Vue.set(this.createArr, row, this.createArr[row].map((prev,i)=>i === col ? this.curTurn : prev))
        this.$root.$emit("onChangeCurTurn", this.curTurn);
        for (let i = 0; i < this.size; i++) {
          for (let j = 0; j < this.size; j++) {
            console.log(this.createArr[i][j])
          }
        }
        this.curTurn = this.curTurn === "X" ? "0" : "X";

      },
    }
  }
</script>

<style scoped>
</style>