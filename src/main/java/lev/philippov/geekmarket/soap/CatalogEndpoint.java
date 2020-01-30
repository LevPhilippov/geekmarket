package lev.philippov.geekmarket.soap;

import lev.philippov.geekmarket.soap.catalog.GetItemListRequest;
import lev.philippov.geekmarket.soap.catalog.GetItemListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CatalogEndpoint {
    private static final String CATALOG_NAMESPACE_URI = "http://www.geekmarket.philippov.lev/soap/catalog";

    private SoapCatalogService soapCatalogService;

    @Autowired
    public void setSoapCatalogService(SoapCatalogService soapCatalogService) {
        this.soapCatalogService = soapCatalogService;
    }

    @PayloadRoot(namespace = CATALOG_NAMESPACE_URI, localPart = "getItemListRequest")
    @ResponsePayload
    public GetItemListResponse getItemListResponse(@RequestPayload GetItemListRequest request) {
        GetItemListResponse response = new GetItemListResponse();
        response.setClientName(request.getClientName());
        response.getSoapItemList().addAll(soapCatalogService.findAllItemDto());
        return response;
    }
}
