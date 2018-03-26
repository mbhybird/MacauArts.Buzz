using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Net.Http;
using System.Web.Http;

namespace Arts.Buzz.Web.Service.Controllers
{
    public class BaseApiController : ApiController
    {
        public beacondb_testEntities db { get; set; }
        public BaseApiController()
        {
            this.db = new beacondb_testEntities();
        }

    }
}
