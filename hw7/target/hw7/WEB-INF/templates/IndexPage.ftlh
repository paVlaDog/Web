<#import "commons.ftlh" as c>

<@c.page>
    <div style="display: flex; flex-direction: column-reverse" class="articlesBox">
        <template>
            <@c.tempPost></@c.tempPost>
        </template>
        <div class="noDataText"></div>

    <script>
        $(function () {
            const $noData = $(".articlesBox .noDataText");
            $noData.text("Loading...");

            ajax ({action: "action"},
                function(response) {
                    $noData.hide();
                    response["posts"].forEach(function(post) {
                        $noData.hide();
                        if (!post["hidden"]) {
                            const $post = $($(".articlesBox template").prop("content")).clone();
                            $post.find(".title").text(post["title"]);
                            $post.find(".post-userId").text(post["userId"]);
                            response["users"].forEach(function(user) {
                                if (user["id"] === post["userId"]) {
                                    $post.find(".post-userLogin").text(user["login"]);
                                }
                            });
                            $post.find(".body").text(post["text"]);
                            $post.find("article").attr("data-userId", post["id"]);
                            $(".articlesBox").append($post);
                        }
                    });
                }
            )
        })
    </script>
</@c.page>
