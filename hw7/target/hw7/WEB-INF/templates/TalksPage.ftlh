<#-- @ftlvariable name="users" type="ru.itmo.wp.model.domain.User[]" -->
<#-- @ftlvariable name="talks" type="ru.itmo.wp.model.domain.Talk[]" -->
<#-- @ftlvariable name="error" type="java.lang.String" -->
<#-- @ftlvariable name="targetUserId" type="java.lang.Long" -->
<#-- @ftlvariable name="text" type="java.lang.String" -->

<#import "commons.ftlh" as c>

<@c.page>
    <div class="form-box">
        <div class="header">Send Message</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="message"/>
                <div class="field">
                    <div style="width: 3rem" class="name">
                        <label for="targetUserId">To</label>
                    </div>
                    <div class="value">
                        <select id="targetUserId" name="targetUserId">
                            <#list users as u>
                                <option value="${u.id}">${u.login}</option>
                            </#list>
                        </select>
<#--                        <input id="targetUserId" name="targetUserId" value="${targetUserId!}"/>-->
                    </div>
                </div>
                <div class="field">
                    <div style="width: 3rem" class="name">
                        <label for="text">Text</label>
                    </div>
                    <div class="value">
                        <textarea id="text" name="text" rows="10" cols="37"></textarea>
<#--                        <input id="text" name="text" type="text" value="${text!}"/>-->
                    </div>
                </div>
                <#if error??>
                    <div class="error">
                        ${error!}
                    </div>
                </#if>
                <div class="button-field">
                    <input type="submit" value="Send Message">
                </div>
            </form>
        </div>
    </div>

    <div  style="padding: 10px 0 0 0" class="datatable">
        <div class="caption">Messages</div>
        <table>
            <thead>
            <tr>
                <th>Id</th>
                <th>From</th>
                <th>To</th>
                <th>Text</th>
                <th>Time</th>
            </tr>
            </thead>
            <tbody>
            <#list talks as talk>
                <tr>
                    <#assign sourceUser = c.findBy(users, "id", talk.sourceUserId)>
                    <#assign targetUser = c.findBy(users, "id", talk.targetUserId)>
                    <td>${talk.id}</td>
                    <td>${sourceUser.login!}</td>
                    <td>${targetUser.login!}</td>
                    <td>${talk.text}</td>
                    <td>${talk.creationTime}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    </div>
</@c.page>
