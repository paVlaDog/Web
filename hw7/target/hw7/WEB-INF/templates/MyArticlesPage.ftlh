<#import "commons.ftlh" as c>

<@c.page>
<div style="display: flex; flex-direction: column-reverse" class="articlesBox">
    <div class="my-articles datatable">
        <div class="caption">Articles</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Hide</th>
            </tr>
            </thead>
            <tbody>
            <#list posts as post>
                <tr>
                    <td>${post.id}</td>
                    <td>${post.title}</td>
                    <td>
                        <div style="width: 3rem; margin: auto">
                            <#if post.hidden == true>
                                <button class="hide-button show" name=${post.userId} value=${post.id}>Show</button>
                            <#else>
                                <button class="hide-button hide" name=${post.userId} value=${post.id}>Hide</button>
                            </#if>
                        </div>
                    </td>
                </tr>
            </#list>
            <div class="error"></div>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            $(".my-articles").find(".hide-button").on('click', function () {
                const $thisButton = $(this);
                if ($thisButton.hasClass('hide')) {
                    ajaxButtonArticle($thisButton, "hidePost", "Show");
                } else {
                    ajaxButtonArticle($thisButton, "showPost", "Hide");
                }
                return false;
            });
        })
    </script>

<#--    <script>-->
<#--        $(function () {-->
<#--            $(".my-articles").find(".showButton").on('click',function () {-->
<#--                const data = {-->
<#--                    action: "showPost",-->
<#--                    value: $(this).val()-->
<#--                };-->

<#--                const $button = $(this);-->

<#--                ajax(data, function(response) {})-->
<#--                $button.text("Hide");-->
<#--                $button.toggleClass('showButton hideButton');-->

<#--                return false;-->
<#--            });-->
<#--        })-->
<#--    </script>-->

    </@c.page>
