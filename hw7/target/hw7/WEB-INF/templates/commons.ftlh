<#-- @ftlvariable name="user" type="ru.itmo.wp.model.domain.User" -->
<#-- @ftlvariable name="message" type="java.lang.String" -->
<#-- @ftlvariable name="userCount" type="java.lang.Long" -->


<#macro page>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Codeforces</title>
    <link rel="stylesheet" type="text/css" href="css/normalize.css">
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <link rel="stylesheet" type="text/css" href="css/form.css">
    <link rel="stylesheet" type="text/css" href="css/datatable.css">
    <link rel="stylesheet" type="text/css" href="css/tictactoe.css">
    <script src="/js/jquery-3.6.1.min.js"></script>
    <script src="/js/notify.min.js"></script>
    <script src="/js/app.js"></script>
</head>
<body>
<header>
    <a href="/index"><img src="img/logo.png" alt="Codeforces" title="Codeforces"/></a>
    <div class="languages">
        <a href="#"><img src="img/gb.png" alt="In English" title="In English"/></a>
        <a href="#"><img src="img/ru.png" alt="In Russian" title="In Russian"/></a>
    </div>
    <div class="enter-or-register-box">
        <#if user??>
            ${user.login}
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
            <li><a href="/index">Home</a></li>
            <li><a href="/users">Users</a></li>
            <li><a href="/talks">Talks</a></li>
            <#if user??>
                <li><a href="/article">Articles</a></li>
                <li><a href="/myArticles">My articles</a></li>
            </#if>
            <li><a href="/ticTacToe">Tic Tac Toe</a></li>
        </ul>
    </nav>
</header>
<div class="middle">
    <aside>
        <section>
            <div class="header">
                Pay attention
            </div>
            <div class="body">
                Lorem ipsum dolor sit amet, consectetur adipisicing elit. Cupiditate ducimus enim facere impedit nobis,
                nulla placeat quam suscipit unde voluptatibus.
            </div>
            <div class="footer">
                <a href="#">View all</a>
            </div>
        </section>
    </aside>
    <main>
        <#nested/>
    </main>
</div>
<footer>
    <p>Registered ${userCount!} users</p>
    <a href="/index">Codeforces</a> &copy; 2022 by Mike Mirzayanov
</footer>
<script>
    $(function () {
        <#if message??>
            notify("${message?js_string}");
        </#if>
    })
</script>
</body>
</html>
</#macro>

<#macro tempPost>
    <article>
        <div class="title"></div>
        <div class="information">
            By
            <a class="post-userLogin"></a>,
            <a class="post-userId"></a>
            days ago, translation
        </div>
        <div class="body"></div>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score post-userId"></span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                <a class="post-userId"></a> <a>days ago</a>
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a class="post-userId" href="#"></a>
            </div>
        </div>
    </article>
</#macro>


<#function findBy mas key value>
    <#list mas as element>
        <#if element[key] == value>
            <#return element>
        </#if>
    </#list>
</#function>

<#macro generatePost post user key>
    <article>
        <div class="title">${post.title}</div>
        <div class="information">
            By
            <a class="post-userLogin"> ${user.login}</a>,
            <a class="post-userId">${post.id}</a>
            days ago, translation
        </div>
        <div class="body">
            <@postText post key/>
        </div>
        <div class="footer">
            <div class="left">
                <img src="img/voteup.png" title="Vote Up" alt="Vote Up"/>
                <span class="positive-score post-userId">+${post.id}</span>
                <img src="img/votedown.png" title="Vote Down" alt="Vote Down"/>
            </div>
            <div class="right">
                <img src="img/date_16x16.png" title="Publish Time" alt="Publish Time"/>
                <a class="post-userId">${post.id}</a> <a>days ago</a>
                <img src="img/comments_16x16.png" title="Comments" alt="Comments"/>
                <a class="post-userId" href="#">${post.id}</a>
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
