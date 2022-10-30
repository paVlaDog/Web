<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#import "./common.ftlh" as c>

<@c.page>

    <#if user_id?has_content && user_id?is_number>
        <#assign ind = c.findIndex(users, "id", user_id)/>
    <#else>
        <#assign ind = -1/>
    </#if>


    <#if ind != -1>
        <div style="display: flex; flex-direction: column-reverse">
            <#list posts as post>
                <#if post.user_id == user_id>
                    <@c.generatePost post "part"/>
                </#if>
            </#list>
        </div>
    <#else>
        <@c.notfound/>
    </#if>



</@c.page>
