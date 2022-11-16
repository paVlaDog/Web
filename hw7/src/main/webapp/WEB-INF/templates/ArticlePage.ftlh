<#-- @ftlvariable name="login" type="java.lang.String" -->
<#-- @ftlvariable name="password" type="java.lang.String" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="createArticle form-box">
        <div class="header">Create your beautiful post</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="createArticle"/>
                <div class="field">
                    <div style="width: 3rem" class="name">
                        <label for="title">Title</label>
                    </div>
                    <div style="width: 22rem" class="value">
                        <input id="title" name="title"/>
                    </div>
                </div>
                <div class="field">
                    <div style="width: 3rem" class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text" rows="10" cols="37"></textarea>
                    </div>
                </div>
                <div class="field">
                    <div style="width: 3rem" class="name">
                        <label for="hidden">Hidden</label>
                    </div>
                    <div class="value">
                        <input type="checkbox" id="hidden" name="hidden"/>
                    </div>
                </div>
                <div class="error"></div>
                <div class="button-field">
                    <input type="submit" value="Create the article">
                </div>
            </form>
        </div>
    </div>

    <script>
        $(function () {
            $(".createArticle form").submit(function () {
                const $error = $(this).find(".error");
                let hide = "str";
                if ($($(this).find("input[name='hidden']")).is(':checked')) {
                    hide = "hidden";
                } else {
                    hide = "visible";
                }
                const data = {
                    action: "createArticle",
                    title: $(this).find("input[name='title']").val(),
                    text: $(this).find("textarea[name='text']").val(),
                    hidden: hide
                };

                ajaxErrAndRedir(data, $error);

                return false;
            });
        })
    </script>
</@c.page>
