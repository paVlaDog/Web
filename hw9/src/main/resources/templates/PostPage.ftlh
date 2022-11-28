<#import "commons.ftlh" as c>

<@c.page>
    <#if post?? && post?has_content>
        <@c.post post=post/>

        <h3>Comments:</h3>
        <ul>
            <#list comments as comment>
                <li>${comment.text} by ${comment.user.login}</li>
            </#list>
        </ul>

        <#if user?? && user?has_content>
            <div class="form">
                <div class="header">Your comment:</div>
                <div class="body">
                    <form method="post" action="">
                        <input type="hidden" name="action" value="writePost"/>
                        <div class="field">
                            <div class="name">
                                <label for="text">Text</label>
                            </div>
                            <div class="value">
                                <textarea id="text" name="text">${newComment.text!}</textarea>
                            </div>
                            <@c.error "newComment.text"/>
                        </div>
                        <div class="button-field">
                            <input type="submit" value="Write">
                        </div>
                    </form>
                </div>
            </div>
        </#if>
    <#else>
        <h2>Post don't exist </h2>
        <h6>like you</h6>
    </#if>



</@c.page>
