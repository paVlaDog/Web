<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <#if user?? && user.admin>
                    <th>Admin</th>
                </#if>
            </tr>
            </thead>
            <tbody>
            <template>
                <tr>
                    <td class="user-id"></td>
                    <td class="user-login"></td>
                    <#if user?? && user.admin>
                        <td class="user-admin">
                            <a class="is-admin"></a>
                            <button class="change-admin"></button>
                        </td>
                    </#if>
                </tr>
            </template>
            <tr class="noData">
                <td colspan="3">
                    No data
                </td>
            </tr>
            </tbody>
        </table>
    </div>

    <script>
        $(function () {
            const $noData = $(".users .noData");
            $noData.find("td").text("Loading...");

            ajax ({action: "findAll"},
                function(response) {
                    response["users"].forEach(function(usr) {
                        $noData.hide();
                        const $tr = $($(".users template").prop("content")).clone();
                        $tr.find(".user-id").text(usr["id"]);
                        $tr.find(".user-login").text(usr["login"]);
                        if (response.hasOwnProperty("user") && response["user"] != null && response["user"]["admin"]) {
                            const $adminCol = $tr.find(".user-admin");
                            if (usr["admin"]) {
                                $adminCol.find(".is-admin").text("Admin");
                                $adminCol.find(".change-admin").text("Disable");
                                $adminCol.find(".change-admin").addClass("disable");
                            } else {
                                $adminCol.find(".is-admin").text("User");
                                $adminCol.find(".change-admin").text("Enable");
                                $adminCol.find(".change-admin").addClass("enable");
                            }
                            $adminCol.find(".change-admin").attr('value', usr["id"]);
                        }
                        $tr.find("tr").attr("data-userId", usr["id"]);
                        $(".users tbody").append($tr);
                    });
                    $(".users .user_login").click(function() {
                        ajax({
                            action: "findUser",
                            userId: $(this).attr("data-userId")
                        }, function (response) {
                            notify(response["user"].creationTime);
                        })
                    });
                    $(".users").find(".user-admin").on('click', function () {
                        const $thisButton = $(this).find(".change-admin");
                        if ($thisButton.hasClass('enable')) {
                            ajaxButtonAdmin($thisButton, "addAdmin", "Disable");
                            $(this).find(".is-admin").text("Admin");
                        } else {
                            ajaxButtonAdmin($thisButton, "removeAdmin", "Enable");
                            $(this).find(".is-admin").text("User");
                        }
                        return false;
                    });
                }
            )
        })
    </script>

    <script>
        $(function () {
            $(".users").find(".change-admin").on('click', function () {
                const $thisButton = $(this);
                if ($thisButton.hasClass('enable')) {
                    ajaxButtonAdmin($thisButton, "addAdmin", "Disable");
                } else {
                    ajaxButtonAdmin($thisButton, "removeAdmin", "Enable");
                }
                return false;
            });
        })
    </script>
</@c.page>

