using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Arts.Buzz.Web.Service.Models
{
    public class ViewAppVersion
    {
        public string version { get; set; }
        public string url { get; set; }
        public string description { get; set; }
        public string url_server { get; set; }
    }

    public class ViewCatalogVersion
    {
        public Guid publishedversion { get; set; }
    }

    public class ViewExContent
    {
        public IList<beacon> beacons { get; set; }
        public IList<content_ex> contents { get; set; }
    }

}