<#import "commons.ftlh" as c>

<@c.page>
    <div class="users datatable">
        <div class="caption">User</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>Login</th>
                <th>Created</th>
                <th>Disabled</th>
            </tr>
            </thead>
            <tbody>
            <#if users?size==0>
                <tr class="noData">
                    <td colspan="3">
                        No data
                    </td>
                </tr>
            <#else>
                <#list users as user>
                <tr>
                    <td class="id">${user.id}</td>
                    <td class="login">${user.login}</td>
                    <td class="creationTime">${user.creationTime}</td>
                    <td class="disabled">
                        <form method="post" action="">
                            <#if !user.disabled>
                                <button class="disable" value="block">
                                    <input type="hidden" id="status" name="status" value="block"/>
                                    <input type="hidden" id="userId" name="userId" value="${user.id}"/>
                                    Block
                                </button>
                            <#else>
                                <button class="enable" value="unblock">
                                    <input type="hidden" id="status" name="status" value="unBlock"/>
                                    <input type="hidden" id="userId" name="userId" value="${user.id}"/>
                                    Unblock
                                </button>
                            </#if>
                        </form>
                    </td>
                </tr>
                </#list>
            </#if>
            </tbody>
        </table>
    </div>
</@c.page>
