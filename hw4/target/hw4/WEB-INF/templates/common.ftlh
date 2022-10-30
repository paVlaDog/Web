<#macro page>
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>Codeforces</title>
        <link rel="stylesheet" href="/css/normalize.css">
        <link rel="stylesheet" href="/css/style.css">
        <link rel="icon" href="/favicon.ico">
    </head>
    <body>
    <@header/>
    <div class="middle">
        <@sidebar/>
        <main>
            <#nested/>
        </main>
    </div>
    <@footer/>
    </body>
    </html>
</#macro>

<#macro header>
    <header>
        <a href="#" class="logo"><img alt="Codeforces" title="Codeforces"
                                      src="/img/codeforces-logo-with-telegram.png"></a>
        <div class="languages">
            <a href="#"><img alt="In English" title="In English"
                             src="/img/gb.png"></a>
            <a href="#"><img alt="In Russian" title="In Russian"
                             src="/img/ru.png"></a>
        </div>
        <div class="enter-or-register-box">
            <#if user??>
                <@userlink user true/>
                |
                <a href="/logout">Logout</a>
            <#else>
                <a href="/enter">Enter</a>
                |
                <a href="/register">Register</a>
            </#if>
        </div>
        <nav>
            <ul>
                <#list headerselement as item>
                    <li>
                        <a <#if currentheaders?has_content && item == currentheaders>
                            style="border-bottom: 3px solid blue"
                            </#if>
                            href="${item.href}">${item.name}
                        </a>
                    </li>
                </#list>
            </ul>
        </nav>
    </header>
</#macro>

<#macro sidebar>
    <aside>
        <div style="display: flex; flex-direction: column-reverse">
            <#list posts as post>
                <section>
                    <div class="header">Post №${post.id}</div>
                    <div class="body"><@postText post "Part"/></div>
                    <div class="footer">
                        <a href="/post?post_id=${post.id}">View all</a>
                    </div>
                </section>
            </#list>
        </div>
    </aside>
</#macro>

<#macro footer>
    <footer>
        2010-2021 &copy; by Mike Mirzayanov
    </footer>
</#macro>


<#macro generatePost post key>
    <article>
        <div class="title">${post.title}</div>
        <div class="information">By ${findBy(users, "id", post.user_id).handle}, ${post.id} days ago, translation</div>
        <div class="body">
            <@postText post key/>
        </div>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score">+${post.id}</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                ${post.id} days ago
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a href="#">${post.id}</a>
            </div>
        </div>
    </article>
</#macro>

<#macro postText post key>
    <#if 350 < post.text?length && key != "All">
        ${post.text?substring(0, 350)}<a style="text-decoration: none" href="/post?post_id=${post.id}">...</a>
    <#else>
        ${post.text}
    </#if>
</#macro>

<#macro notfound>
    <h1>404</h1> Page not found
</#macro>

<#macro userlink user nameOnly=false>
<#-- @ftlvariable name="user" type="ru.itmo.web.hw4.model.User" -->
    <#if nameOnly == true>
        <a href="/user?handle=${user.handle}">${user.name}</a>
    <#else>
        <a class="paintedUser user-${user.color}" href="/user?handle=${user.handle}">${user.handle}</a>
    </#if>
</#macro>

<#function findBy items key value>
    <#if !value?has_content>
        <#return "none"/>
    </#if>
    <#list items as item>
        <#if item[key] == value>
            <#return item/>
        </#if>
    </#list>
</#function>

<#function findIndex items key value>
    <#if !value?has_content>
        <#return -1/>
    </#if>
    <#list items as item>
        <#if item[key] == value>
            <#return item_index/>
        </#if>
    </#list>
    <#return -1>
</#function>

<#function findCur items ind>
    <#if (ind >= 0) && (ind < items?size)>
        <#return items[ind]>
    <#else>
        <#return "none">
    </#if>
</#function>

<#function findNext items ind>
    <#if (ind >= 0) && (ind < items?size - 1)>
        <#return items[ind + 1]>
    <#else>
        <#return "none">
    </#if>
</#function>

<#function findPrev items ind>
    <#if (ind > 0) && (ind < items?size)>
        <#return items[ind - 1]>
    <#else>
        <#return "none">
    </#if>
</#function>

<#function count items key value>
    <#if !value?has_content>
        <#return "none"/>
    </#if>
    <#assign i = 0>
    <#list items as item>
        <#if item[key] == value>
            <#assign i = i + 1>
        </#if>
    </#list>
    <#return i/>
</#function>


