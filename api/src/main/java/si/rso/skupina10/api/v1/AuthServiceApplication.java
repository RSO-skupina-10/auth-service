package si.rso.skupina10.api.v1;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
@OpenAPIDefinition(info = @Info(title = "Auth Service API", version = "v1",
        contact = @Contact(email = "kskrlj@rso.si"),
        license = @License(name="dev"), description = "API for managing and getting users info"),
        servers = @Server(url = "http://localhost:8084/")
)
@ApplicationPath("/v1")
public class AuthServiceApplication extends Application {
}