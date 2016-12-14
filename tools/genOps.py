template = """
    {messages}
    <wsdl:portType name="{portType}">
        {operations}
    </wsdl:portType>
    <wsdl:binding name="{binding}" type="tns:{portType}">
        <soap:binding style="document" transport="http://schemas.xmlsoap.org/soap/http"/>
        {bindingOperations}
    </wsdl:binding>
    <wsdl:service name="{service}">
        <wsdl:port name="{port}" binding="tns:{binding}">
            <soap:address location="Empty"/>
        </wsdl:port>
    </wsdl:service>
"""

messageTpl = """
    <wsdl:message name="{request[name]}">
        <wsdl:part name="parameter" element="tns:{request[element]}"/>
    </wsdl:message>
    <wsdl:message name="{response[name]}">
        <wsdl:part name="parameter" element="tns:{response[element]}"/>
    </wsdl:message>
"""

operationTpl = """
        <wsdl:operation name="{name}">
            <wsdl:input message="tns:{request}"/>
            <wsdl:output message="tns:{response}"/>
        </wsdl:operation>
"""

bindingOperationTpl = """
        <wsdl:operation name="{name}">
            <wsdl:input>
                <soap:body use="literal"/>
            </wsdl:input>
            <wsdl:output>
                <soap:body use="literal"/>
            </wsdl:output>
        </wsdl:operation>
"""


result = {
    'port': 'MediaPort',
    'portType': 'MediaPortType',
    'binding': 'MediaBinding',
    'service': 'MediaService',
    'messages': '',
    'operations': '',
    'bindingOperations': ''
}


# Explicitly defined requests and responses
# operations = [
#     {
#     'operation': 'GetGenreByIdOrName',
#     'request': {'name': 'GetGenreByIdOrNameRequest', 'element': 'getGenreByIdOrNameRequest'},
#     'response': {'name': 'GetGenreByIdOrNameResponse', 'element': 'getGenreByIdOrNameResponse'}
#     }
# ]
# for op in operations:
#     result['messages'] += messageTpl.format_map(op)
#     result['operations'] += operationTpl.format_map({
#         'name': op['operation'],
#         'request': op['request']['name'],
#         'response': op['response']['name']
#     })
#     result['bindingOperations'] += bindingOperationTpl.format_map({'name': op['operation']})

operations = [
    'GetGenreByIdOrName',
    'AddNewGenre',
    'EditGenre',
    'GetAllGenres',
    'GetMediaById',
    'AddNewMedia',
    'AddMediaRating',
    'EditMedia',
    'GetMediaByGenre',
    'GetAllMedia'
]

for op in operations:
    lowercased_op = op.replace(op[0], op[0].lower(), 1)
    op_map = {
        'operation': op,
        'request': {'name': op + 'Request', 'element': lowercased_op + 'Request'},
        'response': {'name': op + 'Response', 'element': lowercased_op + 'Response'}
    }
    result['messages'] += messageTpl.format_map(op_map)
    result['operations'] += operationTpl.format_map({
        'name': op_map['operation'],
        'request': op_map['request']['name'],
        'response': op_map['response']['name']
    })
    result['bindingOperations'] += bindingOperationTpl.format_map({'name': op_map['operation']})

print(template.format_map(result))
