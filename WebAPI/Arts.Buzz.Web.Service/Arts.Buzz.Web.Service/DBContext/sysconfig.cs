using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace Arts.Buzz.Web.Service
{
    using System;
    using System.Collections.Generic;

    public partial class sysconfig
    {
        public int id { get; set; }
        public int ballviewrange { get; set; }
        public int ballsizestep { get; set; }
        public string extag { get; set; }
    }
}
