package ru.itmo.web.hw4.util;

import ru.itmo.web.hw4.model.Color;
import ru.itmo.web.hw4.model.HeadersElement;
import ru.itmo.web.hw4.model.Post;
import ru.itmo.web.hw4.model.User;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DataUtil {
    private static final List<User> USERS = Arrays.asList(
            new User(1, "MikeMirzayanov", "Mike Mirzayanov", Color.BLUE),
            new User(6, "pashka", "Pavel Mavrin", Color.RED),
            new User(9, "geranazavr555", "Georgiy Nazarov", Color.GREEN),
            new User(11, "tourist", "Gennady Korotkevich", Color.BLUE)
    );

    private static final List<HeadersElement> HEADERS_ELEMENTS = Arrays.asList(
            new HeadersElement("/index", "Home"),
            new HeadersElement("/misc/help", "Help"),
            new HeadersElement("/contests", "Contests"),
            new HeadersElement("/users", "users")
    );

    private static final List<Post> POSTS = Arrays.asList(
            new Post(1, "First post", "This is a really first post\n", 1),
            new Post(2, "It's my life...", "It's now or never\n" +
                    "I ain't gonna live forever", 1),
            new Post(3, "Hi", "Hello\n", 9),
            new Post(4, "Very long text", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. At consectetur delectus deleniti dolore earum est eveniet ex fugit id iste iusto maiores molestias natus, neque non odio pariatur soluta voluptas. Architecto beatae commodi corporis cupiditate delectus deleniti dolor, dolores, ducimus eaque eum fuga hic illo in mollitia odit perferendis provident quaerat quasi qui quibusdam quis repellat repudiandae rerum sit tenetur voluptatem voluptates? Consequuntur, iste omnis! Expedita fugit nemo neque nostrum sint soluta! Adipisci aut debitis deserunt error libero nihil non officia pariatur perferendis temporibus? Accusamus animi consectetur, ducimus eius eligendi est minus, praesentium, quis quisquam reprehenderit repudiandae rerum sit vel.", 9)
    );

    public static void addData(HttpServletRequest request, Map<String, Object> data) {
        data.put("users", USERS);
        data.put("headerselement", HEADERS_ELEMENTS);
        data.put("posts", POSTS);

        for (User user : USERS) {
            if (Long.toString(user.getId()).equals(request.getParameter("logged_user_id")) ||
                    Long.toString(user.getId()).equals(request.getParameter("user_id")) ) {
                data.put("user", user);
            }
        }

        for (HeadersElement he : HEADERS_ELEMENTS) {
            if (he.getHref().equals(request.getRequestURI())) {
                data.put("currentheaders", he);
            }
        }

//        for (Post post : Posts) {
//            if (post.getHref().equals(request.getRequestURI())) {
//                data.put("currentheaders", post);
//            }
//        }
    }
}
