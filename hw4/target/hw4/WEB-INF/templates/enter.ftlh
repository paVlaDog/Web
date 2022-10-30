<#import "./common.ftlh" as c>

<@c.page>
    <p>Choose your character</p>
    <#list users as user>
        <a class="paintedUser user-${user.color}" style="display: block" href="/index?logged_user_id=${user.id}">${user.name}/${user.handle}</a>
    </#list>
</@c.page>