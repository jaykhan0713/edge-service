package com.jay.edge.bootstrap.transport.http.headers.binding;

import java.util.Locale;

import org.springframework.stereotype.Component;

import com.jay.edge.bootstrap.transport.http.properties.TransportHttpProperties;
import com.jay.edge.core.port.transport.http.IdentityHeadersProvider;
import com.jay.edge.core.transport.http.IdentityHeaders;

@Component
public class PropertiesIdentityHeadersResolver implements IdentityHeadersProvider {

    private final IdentityHeaders identityHeaders;

    public PropertiesIdentityHeadersResolver(TransportHttpProperties props) {
        this.identityHeaders = resolve(props);
    }

    @Override
    public IdentityHeaders identityHeaders() {
        return identityHeaders;
    }

    private static IdentityHeaders resolve(TransportHttpProperties props) {
        var headers = props.http().headers();
        return new IdentityHeaders(
                headers.userId().trim().toLowerCase(Locale.ROOT), //normalize trim and lower case.
                headers.requestId().trim().toLowerCase(Locale.ROOT)
        );
    }
}
