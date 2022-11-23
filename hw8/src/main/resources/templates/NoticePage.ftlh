<#-- @ftlvariable name="enterForm" type="ru.itmo.wp.form.UserCredentials" -->
<#import "/spring.ftl" as spring>
<#import "commons.ftlh" as c>

<@c.page>
    <div class="addNotice form-box">
        <div class="header">Enter</div>
        <div class="body">
            <form method="post" action="">
                <input type="hidden" name="action" value="addNotice"/>
                <div class="field">
                    <div class="name" style="width: 7rem">
                        <label for="content">Content</label>
                    </div>
                    <div class="value">
                        <textarea id="content" name="content" rows="10" cols="30">${noticeForm.content!}</textarea>
                    </div>
                    <@c.error "noticeForm.content"/>
                </div>
                <div class="button-field">
                    <input type="submit" value="Add notice">
                </div>
            </form>
        </div>
    </div>
</@c.page>
