<#-- @ftlvariable name="handle" type="java.lang.String" -->
<#-- @ftlvariable name="user_id" type="java.lang.Long" -->
<#-- @ftlvariable name="id" type="java.lang.Long" -->

<#import "./common.ftlh" as c>

<@c.page>

    <#if handle?has_content>
        <#assign ind = c.findIndex(users, "handle", handle)/>
    <#elseif user_id?has_content && user_id?is_number>
        <#assign ind = c.findIndex(users, "id", user_id)/>
    <#else>
        <#assign ind = -1/>
    </#if>

    <#if ind == -1>
        <@c.notfound/>
    <#else>
        <#assign u = c.findCur(users, ind)!/>
        <#assign un = c.findNext(users, ind)!/>
        <#assign up = c.findPrev(users, ind)!/>

        <div class="userBlock">
            <div class="rightPartUserProfile">
                <div class="headerUserName">
                    <a href="${u.handle}">${u.name} </a>
                </div>
                <ul class="userInformation">
                    <li>id: ${u.id}</li>
                    <li>handle: ${u.handle}</li>
                    <li>name: ${u.name}</li>
                    <li>
                        posts:
                        <a href="/posts?user_id=${u.id}">
                            <#assign postCount = c.count(posts, "user_id", u.id)>
                            ${postCount}
                        </a>
                    </li>
                </ul>
            </div>
            <div class="leftPartUserProfile">
                <img src="/img/user-icon.png">
            </div>
        </div>
        <div class="userProfileArrows">
            <#if up != "none">
                <a href="user?handle=${up.handle}">&#8592</a>
            <#else>
                <p>&#8592</p>
            </#if>
            <#if un != "none">
                <a href="user?handle=${un.handle}">&#8594</a>
            <#else>
                <p>&#8594</p>
            </#if>
        </div>

    </#if>
</@c.page>
