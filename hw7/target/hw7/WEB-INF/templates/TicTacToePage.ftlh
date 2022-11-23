<#-- @ftlvariable name="state" type="ru.itmo.wp.web.page.TicTacToePage.State" -->
<#import "./commons.ftlh" as c>

<@c.page>
    <h1>Tic-Tac-Toe</h1>

    <#if !state??>
        <div class="enter form-box">
            <div class="header">New Game</div>
            <div class="body">
                <form method="post" action="userState">
                    <input type="hidden" name="action" value="enter"/>
                    <div class="field">
                        <div class="name">
                            <label for="size">Size:</label>
                        </div>
                        <div class="value">
                            <input id="size" name="size"/>
                        </div>
                    </div>
                    <div class="field">
                        <div class="name">
                            <label for="needToWin">Need to win:</label>
                        </div>
                        <div class="value">
                            <input id="needToWin" name="needToWin"/>
                        </div>
                    </div>
                    <div class="error"></div>
                    <div class="button-field">
                        <input type="submit" value="Enter">
                    </div>
                </form>
            </div>
        </div>

        <script>
            $(function () {
                $(".enter form").submit(function () {
                    const data = {
                        action: "userState",
                        size: $(this).find("input[name='size']").val(),
                        needToWin: $(this).find("input[name='needToWin']").val()
                    };
                    ajax(data, function(response) {
                        if (response["redirect"]) {
                            location.href = response["redirect"];
                        }
                    })
                    return false;
                });
            })
        </script>
    <#else>
        <div class="tictactoe-board">
            <#list 0..state.size-1 as row>
                <div class="tictactoe-row">
                    <#list 0..state.size-1 as col>
                        <form class="tictactoe-form" method="post">
                            <input type="hidden" name="action" value="onMove">
                            <input type="submit" <#if state.cells[row][col]?has_content>disabled</#if>
                                   class="tictactoe-cell"
                                   name="cell_${row}-${col}" value="${state.cells[row][col]!" "}">
                        </form>
                    </#list>
                </div>
            </#list>

            <script>
                $(function () {
                    $(".tictactoe-board").find(".tictactoe-form").submit(function () {
                        console.log("lol");
                        const data = {
                            action: "onMove",
                            name: $(this).find(".tictactoe-cell").getAttribute("name"),
                        };
                        console.log(data[name]);
                        ajax(data, function(response) {
                            if (response["cellsValue"]) {
                                $(this).find(".tictactoe-cell").disable();
                            }
                        })
                        return false;
                    })

                    // $(".tictactoe-board").find(".tictactoe-cell").submit(function () {
                    //     console.log("lol");
                    //     const data = {
                    //         action: "onMove",
                    //         name: $(this).getAttribute("name"),
                    //     };
                    //     ajax(data, function(response) {
                    //         if (response["redirect"]) {
                    //             location.href = response["redirect"];
                    //         }
                    //     })
                    //     return false;
                    // });
                })
            </script>
        </div>

        <#assign phase=state.phase/>

        <div class="tictactoe-message">
            <#if phase=="RUNNING">
                Move: <#if state.crossesMove>X<#else>O</#if>
            <#elseif phase=="WON_X">
                Game over. Xs won!
            <#elseif phase=="WON_O">
                Game over. Os won!
            <#elseif phase=="DRAW">
                Game over. Draw!
            </#if>
        </div>

        <form method="post">
            <input type="hidden" name="action" value="newGame">
            <input type="submit" class="tictactoe-new-game" name="newGame" value="New Game!">
        </form>



    </#if>
</@c.page>
