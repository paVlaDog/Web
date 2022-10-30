<#import "./common.ftlh" as c>

<@c.page>
    <div class="caption">Users:</div>
    <table>
        <thead>
        <tr>
            <th>Id</th>
            <th>Name</th>
            <th>Handle</th>
            <th>Color</th>
        </tr>
        </thead>
        <tbody>
            <#list users as u>
                <tr>
                    <td>${u.id}</td>
                    <td>${u.name}</td>
                    <td><@c.userlink u/></td>
                    <td>${u.color}</td>
                </tr>
            </#list>
        </tbody>
    </table>
</@c.page>